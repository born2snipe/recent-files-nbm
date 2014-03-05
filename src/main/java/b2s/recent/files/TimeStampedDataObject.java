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

import java.util.Date;
import org.openide.loaders.DataObject;

/**
 *
 * @author markiewb
 */
public class TimeStampedDataObject {
    private DataObject dataObject;
    private Date timestamp;

    private TimeStampedDataObject(DataObject dataObject, Date timeOfAdding) {
        this.dataObject = dataObject;
        this.timestamp = timeOfAdding;
    }

    public TimeStampedDataObject(DataObject dataObject) {
        this(dataObject, new Date());
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    
    
}
