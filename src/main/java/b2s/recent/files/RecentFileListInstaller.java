/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package b2s.recent.files;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openide.loaders.DataObject;
import org.openide.modules.ModuleInstall;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class RecentFileListInstaller extends ModuleInstall implements PropertyChangeListener  {
    private static EditorUtil editorUtil = new EditorUtil();
    public static List<DataObject> dataObjects = Collections.synchronizedList(new ArrayList());

    @Override
    public void restored() {
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {
                dataObjects.addAll(editorUtil.currentFilesOpened());;
                TopComponent.getRegistry().addPropertyChangeListener(RecentFileListInstaller.this);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        DataObject dataObject = editorUtil.dataObject(evt.getNewValue());
        if (dataObject == null) return;

        if (TopComponent.Registry.PROP_TC_OPENED.equals(name)) {
            dataObjects.add(dataObject);
        }
    }
}
