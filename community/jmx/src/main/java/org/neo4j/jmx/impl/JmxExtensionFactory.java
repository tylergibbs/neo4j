/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.jmx.impl;

import org.neo4j.helpers.Service;
import org.neo4j.kernel.database.Database;
import org.neo4j.kernel.extension.ExtensionFactory;
import org.neo4j.kernel.extension.context.ExtensionContext;
import org.neo4j.kernel.internal.KernelData;
import org.neo4j.kernel.lifecycle.Lifecycle;
import org.neo4j.logging.internal.LogService;

import static org.neo4j.kernel.extension.ExtensionType.DATABASE;

@Service.Implementation( ExtensionFactory.class )
@Deprecated
public final class JmxExtensionFactory extends ExtensionFactory<JmxExtensionFactory.Dependencies>
{
    public interface Dependencies
    {
        KernelData getKernelData();

        LogService getLogService();

        Database getDatabase();
    }

    public static final String KEY = "kernel jmx";

    public JmxExtensionFactory()
    {
        super( DATABASE, KEY );
    }

    @Override
    public Lifecycle newInstance( ExtensionContext context, Dependencies dependencies )
    {
        return new JmxExtension( dependencies.getKernelData(),
                dependencies.getDatabase(),
                dependencies.getLogService().getInternalLogProvider() );
    }
}
