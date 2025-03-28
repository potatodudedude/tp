package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.logic.Logic;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Represents a tutorial tab.
 */
public class TutorialTabPane extends UiPart<TabPane> {
    private static final String FXML = "TutorialTabPane.fxml";

    private final List<Person> personList;
    private final String moduleName;
    private final Map<String, Integer> tutorialMap;
    private final Logic logic;

    @FXML
    private TabPane tutorialTabPane;

    /**
     * Constructor for a tutorial tab
     *
     * @param personList the list of persons to convert to tutorial tab view
     */
    public TutorialTabPane(ObservableList<Person> personList, String moduleName, Logic logic) {
        super(FXML);
        this.personList = personList;
        this.logic = logic;
        this.moduleName = moduleName;
        tutorialMap = ModTutGroup.getModuleMap().get(moduleName);
        init();
    }

    private void init() {
        setTabs(new ArrayList<>());

        tutorialTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String newTutorialTab = newValue.getText();
            this.logic.setSelectedTabs(this.moduleName, newTutorialTab);
        });
    }

    private void setTabs(List<String> tabs) {
        for (String tutorialName : tutorialMap.keySet()) {
            ObservableList<Person> filteredList = personList.stream().filter(p -> {
                Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                Stream<Tutorial> tutorialStream = modTutGroups.stream().map(ModTutGroup::getTutorial);
                return tutorialStream.anyMatch(m -> m.getName().equals(tutorialName));
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));

            Tab tab = new Tab(tutorialName);
            tab.setClosable(false);
            tab.setContent(new PersonListPanel(filteredList).getRoot());
            tutorialTabPane.getTabs().add(tab);
        }
    }
}
