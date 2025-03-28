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
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.logic.Logic;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ModTutGroup;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;

/**
 * Represents a module tab.
 */
public class ModuleTabPane extends UiPart<TabPane> {
    private static final String FXML = "ModuleTabPane.fxml";

    private static final Map<String, Map<String, Integer>> moduleMap = ModTutGroup.getModuleMap();

    private final List<Person> personList;
    private final Logic logic;

    @FXML
    private TabPane moduleTabPane;

    /**
     * Constructor for a module tab
     *
     * @param personList the list of persons to convert to module tab view
     */
    public ModuleTabPane(ObservableList<Person> personList, Logic logic) {
        super(FXML);
        this.personList = personList;
        this.logic = logic;
        init();
    }

    private void init() {
        List<String> tabs = new ArrayList<>();
        if (moduleMap.isEmpty()) {
            return;
        }
        String firstModule = moduleMap.keySet().iterator().next();
        String firstTutorial = moduleMap.get(firstModule).keySet().iterator().next();

        tabs.add(firstModule);
        tabs.add(firstTutorial);
        setTabs(tabs);
        logic.setSelectedTabs(firstModule, firstTutorial);

        moduleTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String newModuleTab = newValue.getText();
            String newTutorialTab = moduleMap.get(newModuleTab).keySet().iterator().next();
            logic.setSelectedTabs(newModuleTab, newTutorialTab);
        });
    }

    private void setTabs(List<String> tabs) {
        moduleTabPane.getTabs().clear();
        for (String moduleName : moduleMap.keySet()) {
            ObservableList<Person> filteredList = personList.stream().filter(p -> {
                Set<ModTutGroup> modTutGroups = p.getModTutGroups();
                Stream<Module> moduleStream = modTutGroups.stream().map(ModTutGroup::getModule);
                return moduleStream.anyMatch(m -> m.getName().equals(moduleName));
            }).collect(Collectors.toCollection(FXCollections::observableArrayList));

            Tab tab = new Tab(moduleName);
            tab.setClosable(false);

            TutorialTabPane tutorialTabPane = new TutorialTabPane(filteredList, moduleName, logic);

            tab.setContent(tutorialTabPane.getRoot());
            moduleTabPane.getTabs().add(tab);
        }
        setSelectedTab(moduleTabPane, tabs, 0);
    }

    public void setSelectedTab(TabPane tabPane, List<String> tabs, int index) {
        if (tabPane == null) {
            tabPane = this.moduleTabPane;
        }

        if (tabs.size() != 2) {
            return;
        }

        int tabIndex = getSelectedTabIndex(tabPane, tabs.get(index));
        if (tabIndex == -1) {
            return;
        }

        String tutorialTab = tabs.get(1);

        tabPane.getSelectionModel().select(tabIndex);
        Node content = tabPane.getTabs().get(tabIndex).getContent();
        if (content instanceof TabPane tutorialTabPane) {
            List<String> updatedTabs = new ArrayList<>(tabs);
            updatedTabs.set(1, tutorialTab);
            setSelectedTab(tutorialTabPane, updatedTabs, 1);
        }
    }

    private int getSelectedTabIndex(TabPane tabPane, String tabName) {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            if (tabPane.getTabs().get(i).getText().equals(tabName)) {
                return i;
            }
        }
        return -1;
    }
}
