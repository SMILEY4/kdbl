package de.ruegnerlukas.sqldsl.core.syntax.expression.condition

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

interface Condition : Expression

class NotCondition(val expression: Expression) : Condition
class IsNullCondition(val expression: Expression) : Condition
class IsNotNullCondition(val expression: Expression) : Condition
class AndCondition(val left: Expression, val right: Expression) : Condition
class OrCondition(val left: Expression, val right: Expression) : Condition
class LikeCondition(val expression: Expression, val pattern: String) : Condition
class BetweenCondition(val expression: Expression, val min: Expression, val max: Expression) : Condition
class EqualCondition(val left: Expression, val right: Expression) : Condition
class LessThanCondition(val left: Expression, val right: Expression) : Condition
class GreaterThanCondition(val left: Expression, val right: Expression) : Condition