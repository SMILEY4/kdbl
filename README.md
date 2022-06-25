# KDBL

Small Kotlin-Library for writing portable and typesafe SQL-statements as Kotlin-Code together with a wrapper around common jdbc-operations.

 

## Example

### 1. Define Tables

```kotlin
object Actor : ActorTableDef()

sealed class ActorTableDef : Table("actor") {
    val id = integer("act_id").primaryKey()
    val fName = text("act_fname").notNull()
    val lName = text("act_lname").notNull()
    val gender = text("act_gender").notNull()

    companion object {
        class ActorTableDefAlias(override val table: TableLike, override val alias: String) : ActorTableDef(), AliasTable
    }

    override fun alias(alias: String): ActorTableDefAlias {
        return ActorTableDefAlias(this, alias)
    }
}


object Movie : MovieTableDef()

sealed class MovieTableDef : Table("movie", true) {
    val id = integer("mov_id").primaryKey()
    val title = text("mov_title")
    val year = integer("mov_year")
    val time = integer("mov_time")
    val lang = text("mov_lang").nullable()
    val dateRelease = text("mov_dt_rel")
    val releaseCountry = text("mov_rel_country")

    companion object {
        class MovieTableDefAlias(override val table: TableLike, override val alias: String) : MovieTableDef(), AliasTable
    }

    override fun alias(alias: String): MovieTableDefAlias {
        return MovieTableDefAlias(this, alias)
    }
}
```

### 2. Create a Database

```kotlin
val database = SingleConnectionDatabase(
    DriverManager.getConnection("jdbc:sqlite::memory:"),
    SQLCodeGeneratorImpl(SQLiteDialect())
)
```

### 3. Create Tables

```kotlin
database
    .startCreate {
        SQL.create(Movie)
    }
    .execute()
```

### 4. Insert Rows

```kotlin
database
    .startInsert {
        SQL
            .insert()
            .into(Actor)
            .columns(Actor.id, Actor.fName, Actor.lName, Actor.gender)
            .items(
                SQL.item()
                    .set(Actor.fName, placeholder("fname"))
                    .set(Actor.lName, placeholder("lname"))
                    .set(Actor.gender, placeholder("gender")),
            )
    }
    .parameters {
        it["fname"] = "Example"
        it["lname"] = "Actor"
        it["gender"] = "?"
    }
    .execute()
```

### 5. Query Rows

```kotlin
database
    .startQuery {
        SQL
            .select(Movie.id, Movie.title)
            .from(Movie)
            .where(Movie.year.isEqual(placeholder("year")))
    }
    .parameter("year", 1996)
    .execute()
    .getMultipleOrNone {
        MovieRecord(
            it.getInt(Movie.id.columnName),
            it.getString(Movie.title.columnName)
        )
    }
```

