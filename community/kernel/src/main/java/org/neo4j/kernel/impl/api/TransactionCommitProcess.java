/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
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
package org.neo4j.kernel.impl.api;

import org.neo4j.kernel.api.exceptions.TransactionFailureException;
import org.neo4j.kernel.impl.transaction.xaframework.TransactionRepresentation;

/*
 * This interface represents the contract for committing a transaction. It's quite
 * straightforward - you pass in a TransactionRepresentation and that's it. A simple
 * implementation of this would be to append to a log and then apply the
 * commands of the representation to the store that generated them. Another could
 * instead of appending to a log, write the transaction over the network to another
 * machine.
 * Since part of committing the transaction is applying it, this also exposes a method
 * for registering store appliers, to be notified once the transaction has been written
 * to the log.
 */
public interface TransactionCommitProcess
{
    void commit( TransactionRepresentation representation ) throws TransactionFailureException;
}
