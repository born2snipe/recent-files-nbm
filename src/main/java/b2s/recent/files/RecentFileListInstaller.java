package b2s.recent.files;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.modules.ModuleInstall;
import org.openide.windows.TopComponent;

public class RecentFileListInstaller extends ModuleInstall implements PropertyChangeListener  {

    @Override
    public void restored() {
        TopComponent.getRegistry().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        if (TopComponent.Registry.PROP_TC_CLOSED.equals(name)) {

        }
        if (TopComponent.Registry.PROP_TC_OPENED.equals(name)) {

        }
        System.out.println("evt.getNewValue() = " + evt.getNewValue());
    }

}
