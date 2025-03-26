package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

public class TutorialTabPane extends UiPart<TabPane> {
    private static final String FXML = "TutorialTabPane.fxml";

    private final Map<String, Integer> tutorialMap;

    @FXML
    private TabPane tutorialTabPane;

    public TutorialTabPane(ObservableList<Person> personList, String moduleName) {
        super(FXML);

        tutorialMap = ModTutGroup.getModuleMap().get(moduleName);
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
