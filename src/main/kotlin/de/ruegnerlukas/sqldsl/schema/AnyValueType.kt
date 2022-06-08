package de.ruegnerlukas.sqldsl.schema

interface AnyValueType

interface NoValueType : AnyValueType

interface NumericValueType : AnyValueType

interface IntValueType : NumericValueType

interface FloatValueType : NumericValueType

interface StringValueType : AnyValueType

interface BooleanValueType : AnyValueType