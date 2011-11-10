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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;

public class WrappedScrollingListener extends KeyAdapter {
	private final JList list;
	
	public WrappedScrollingListener(JList list) {
		this.list = list;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		int lastRow = list.getLastVisibleIndex();
		boolean isTopRow = list.getSelectedIndex() == 0;
		boolean isBottomRow = list.getSelectedIndex() == lastRow;
		
		if (isTopRow || isBottomRow) {
			if (moveUp(ke) && isTopRow) {
				list.setSelectedIndex(lastRow);
				ke.consume();
			} else if (moveDown(ke) && isBottomRow) {
				list.setSelectedIndex(0);
				ke.consume();
			}
		}
	}
	
	private boolean moveUp(KeyEvent ke) {
		return ke.getKeyCode() == KeyEvent.VK_UP;
	}
	
	private boolean moveDown(KeyEvent ke) {
		return ke.getKeyCode() == KeyEvent.VK_DOWN;
	}
	
}
