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

import java.util.ArrayList;
import java.util.List;
import org.openide.loaders.DataObject;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;

public class EditorUtil {

    public void activateEditorFor(DataObject findDataObject) {
        for (TopComponent topComponent : TopComponent.getRegistry().getOpened()) {
            DataObject dataObject = dataObject(topComponent);
            if (dataObject != null && dataObject.equals(findDataObject)) {
                topComponent.requestActive();
            }
        }
    }

    public boolean hasEditorAlready(DataObject dataObject) {
        return currentFilesOpened().contains(dataObject);
    }

    public DataObject activeEditor() {
        return dataObject(TopComponent.getRegistry().getActivated());
    }

    public List<DataObject> currentFilesOpened() {
        List<DataObject> dataObjects = new ArrayList<DataObject>();
        for (TopComponent topComponent : TopComponent.getRegistry().getOpened()) {
            DataObject dataObject = dataObject(topComponent);
            if (dataObject != null) dataObjects.add(dataObject);
        }
        return dataObjects;
    }

    public DataObject dataObject(Object obj) {
        if (obj instanceof CloneableTopComponent) {
            CloneableTopComponent topComponent = (CloneableTopComponent) obj;
            return topComponent.getLookup().lookup(DataObject.class);
        }
        return null;
    }
}
