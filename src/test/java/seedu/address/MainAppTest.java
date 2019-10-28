package seedu.address;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Init;

import javafx.scene.input.KeyCode;

@ExtendWith(ApplicationExtension.class)
public class MainAppTest extends ApplicationTest {

    @Init
    public void init() throws Exception {
        ApplicationTest.launch(MainApp.class);
    }

    @Test
    public void traverseTabBarTest(FxRobot robot) {
        var patientsTabStyleClass = robot.lookup("#patientsTab").query().getStyleClass();
        var appointmentsTabStyleClass = robot.lookup("#appointmentsTab").query().getStyleClass();
        var doctorsTabStyleClass = robot.lookup("#doctorsTab").query().getStyleClass();
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var tabBar = robot.lookup("#tabBar").query();

        robot.clickOn("#doctorsTab");
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("selected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.UP);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("selected-tab");

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(patientsTabStyleClass).containsOnly("selected-tab");
        Assertions.assertThat(appointmentsTabStyleClass).containsOnly("unselected-tab");
        Assertions.assertThat(doctorsTabStyleClass).containsOnly("unselected-tab");

        robot.type(KeyCode.TAB);
        Assertions.assertThat(tabBar).isNotFocused();
        Assertions.assertThat(commandBox).isFocused();

        robot.type(KeyCode.TAB);
        Assertions.assertThat(tabBar).isFocused();
        Assertions.assertThat(commandBox).isNotFocused();
    }

    @Test
    public void autoCompleterTest(FxRobot robot) {
        var aco = robot.lookup("#autoCompleteOverlay").queryListView();

        robot.clickOn("#commandTextField");
        robot.write('a').type(KeyCode.UP);
        Assertions.assertThat(aco).isVisible();
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(3);

        robot.type(KeyCode.DOWN);
        Assertions.assertThat(aco.getSelectionModel().getSelectedIndex()).isEqualTo(0);
        robot.eraseText(1);
    }

    @Test
    public void traverseAndUnknownCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("lol").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay).hasText("Unknown command");

        robot.type(KeyCode.UP);
        Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(3);

        robot.type(KeyCode.LEFT, KeyCode.BACK_SPACE);
        Assertions.assertThat(commandBox.getCaretPosition()).isEqualTo(1);
        robot.type(KeyCode.RIGHT).eraseText(2);
    }

    @Test
    public void enqueueAndDequeueTest(FxRobot robot) {
        robot.clickOn("#commandTextField").write("enqueue 001A").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(1);
        robot.write("dequeue 1").type(KeyCode.ENTER);
        Assertions.assertThat(lookup("#queueListView").queryListView()).hasExactlyNumItems(0);
    }

    @Test
    public void invalidCommandFormatTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("addappt").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Invalid command format!");

        robot.eraseText(7);
    }

    @Test
    public void helpCommandTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        var resultDisplay = robot.lookup("#resultDisplay").queryTextInputControl();

        robot.clickOn(commandBox).write("help").type(KeyCode.ENTER);
        Assertions.assertThat(resultDisplay.getText()).startsWith("Opened help window.");
        Assertions.assertThat(robot.window("Help")).isShowing();
        Assertions.assertThat(robot.listWindows().size()).isEqualTo(2);
    }

    @Test
    public void commandBoxHistoryTest(FxRobot robot) {
        var commandBox = robot.lookup("#commandTextField").queryTextInputControl();
        String[] testStrings = {"blabla", "   jUmPs. "};

        robot.clickOn(commandBox).write(testStrings[0]).type(KeyCode.ENTER).type(KeyCode.UP);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[0]);
        robot.eraseText(testStrings[0].length()).write(testStrings[1]).type(KeyCode.ENTER).type(KeyCode.UP);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[1]);
        robot.type(KeyCode.UP, 2);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[0]);
        robot.type(KeyCode.DOWN);
        Assertions.assertThat(commandBox.getText()).isEqualTo(testStrings[1]);
        robot.type(KeyCode.DOWN, 3);
        Assertions.assertThat(commandBox.getText()).isBlank();
    }
}
