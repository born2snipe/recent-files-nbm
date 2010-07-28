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
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;
import org.openide.loaders.DataObject;

public class DataObjectListModel extends AbstractListModel {
    private List<DataObject> rows = Collections.synchronizedList(new ArrayList<DataObject>());

    public DataObjectListModel(List<DataObject> dataObjects) {
        rows.addAll(dataObjects);
        this.fireIntervalAdded(rows, 0, rows.size());
    }

    @Override
    public int getSize() {
        return rows.size();
    }

    @Override
    public Object getElementAt(int index) {
        return rows.get(index);
    }

}
