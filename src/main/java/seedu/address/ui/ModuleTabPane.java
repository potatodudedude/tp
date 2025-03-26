package seedu.address.ui;

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

public class ModuleTabPane extends UiPart<TabPane> {
    private static final String FXML = "ModuleTabPane.fxml";

    private static final Map<String, Map<String, Integer>> moduleMap = ModTutGroup.getModuleMap();

    @FXML
    private TabPane moduleTabPane;

    public ModuleTabPane(ObservableList<Person> personList) {
        super(FXML);

        for (String moduleName : moduleMap.keySet()) {
            ObservableList<Person> filteredList = personList.stream().filter(p -> {
                Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                Stream<Module> moduleStream = modTutGroups.stream().map(ModTutGroup::getModule);
                return moduleStream.anyMatch(m -> m.getName().equals(moduleName));
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));

            Tab tab = new Tab(moduleName);
            tab.setClosable(false);
            tab.setContent(new TutorialTabPane(filteredList, moduleName).getRoot());
            moduleTabPane.getTabs().add(tab);
        }
    }
}
