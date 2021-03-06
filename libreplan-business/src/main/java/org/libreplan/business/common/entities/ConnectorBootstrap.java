/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2013 Igalia, S.L.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.libreplan.business.common.entities;

import org.libreplan.business.common.daos.IConnectorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Creates the LibrePlan {@link Connector Connectors} with its configuration
 * properties and default values.
 *
 * @author Manuel Rego Casasnovas <rego@igalia.com>
 */
@Component
@Scope("singleton")
public class ConnectorBootstrap implements IConnectorBootstrap {

    @Autowired
    private IConnectorDAO connectorDAO;

    @Override
    @Transactional
    public void loadRequiredData() {
        for (PredefinedConnectors predefinedConnector : PredefinedConnectors.values()) {
            String name = predefinedConnector.getName();

            Connector connector = connectorDAO.findUniqueByName(name);
            if ( connector == null ) {
                connector = Connector.create(name);
                connector.setProperties(predefinedConnector.getProperties());
                connectorDAO.save(connector);
            }
        }
    }

}
