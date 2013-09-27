/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.rdb.core.actions.object.mongodb;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.hangum.tadpold.commons.libs.core.define.PublicTadpoleDefine;
import com.hangum.tadpole.dao.mysql.TableDAO;
import com.hangum.tadpole.exception.dialog.ExceptionDetailsErrorDialog;
import com.hangum.tadpole.mongodb.core.dialogs.msg.TadpoleSimpleMessageDialog;
import com.hangum.tadpole.mongodb.core.query.MongoDBQuery;
import com.hangum.tadpole.rdb.core.Activator;
import com.hangum.tadpole.rdb.core.actions.object.AbstractObjectAction;

/**
 * mongoDB collection stats
 * 
 * @author hangum
 *
 */
public class ObjectMongodbCollStates extends AbstractObjectAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ObjectMongodbCollStates.class);

	public final static String ID = "com.hangum.db.browser.rap.core.actions.object.mongo.collection.stats";
	
	public ObjectMongodbCollStates(IWorkbenchWindow window, PublicTadpoleDefine.DB_ACTION actionType, String title) {
		super(window, actionType);
		setId(ID + actionType.toString());
		setText(title);
	}

	@Override
	public void run() {
		if(null != this.sel) {
			TableDAO collDAO = (TableDAO)this.sel.getFirstElement();
			
			try {
				String strCollStats = MongoDBQuery.getCollStats(getUserDB(), collDAO.getName());
				TadpoleSimpleMessageDialog dialog = new TadpoleSimpleMessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
								collDAO.getName()  + " stats", 
								strCollStats);
				dialog.open();
			} catch(Exception e) {
				logger.error("Collection stats", e); //$NON-NLS-1$
				Status errStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e); //$NON-NLS-1$
				ExceptionDetailsErrorDialog.openError(null, "Error", "Collection stats", errStatus); //$NON-NLS-1$
			}
			
		}		
	}
	
}
