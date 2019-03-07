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
package org.neo4j.cypher.internal.compiler.v4_0.planner.logical.steps

import org.neo4j.cypher.internal.compiler.v4_0.planner.logical.{CandidateGenerator, LogicalPlanningContext}
import org.neo4j.cypher.internal.compiler.v4_0.planner.unsolvedPreds
import org.neo4j.cypher.internal.ir.{QueryGraph, InterestingOrder}
import org.neo4j.cypher.internal.logical.plans.LogicalPlan
import org.neo4j.cypher.internal.v4_0.expressions.{HasLabels, Variable}

case object selectHasLabelWithJoin extends CandidateGenerator[LogicalPlan] {

  def apply(plan: LogicalPlan, queryGraph: QueryGraph, interestingOrder: InterestingOrder, context: LogicalPlanningContext): Seq[LogicalPlan] =
    unsolvedPreds(context.planningAttributes.solveds)(queryGraph.selections, plan).collect {
      case s@HasLabels(id: Variable, Seq(labelName)) =>
        val labelScan = context.logicalPlanProducer.planNodeByLabelScan(id.name, labelName, Seq(s), None, Set.empty, context)
        context.logicalPlanProducer.planNodeHashJoin(Set(id.name), plan, labelScan, Seq.empty, context)
    }
}
