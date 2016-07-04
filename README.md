# AndroidPersistenceAPI
AndroidPersistenceAPI (APA)

Inspiring by JPA (Java Persistence API), i thought of exposing similar sort of thing for Android as well. Because maintaining a database and then all the operations via sqlite usually takes time. Besides it would be a great ease for the developer to just create a model with few annoatations and simply uses database operations. 

The libaray expects assets/apa.properties file containing following information:
```
databaseName = test
databaseVersion = 1
```

Sample Model
```java
@Entity
public class MyModel extends ApaEntity{

    @Order(value = 1)
    @Column(name="id", primaryKey = true, type = Type.INTEGER)
    private int id;

    @Order(value = 2)
    @Column(name="name", notNull = true)
    private String name;

    @Order(value = 3)
    @Column(name="flag", notNull = true, type = Type.INTEGER)
    private boolean flag;

    @Order(value = 4)
    @Column(name="value", notNull = true, type = Type.FLOAT)
    private double value;

    public MyModel(int id, String name, int flag, double value) {
        this.id = id;
        this.name = name;
        this.flag = flag!=0;
        this.value = value;
    }

    @Override
    public Class getEntityClass() {
        return MyModel.class;
    }

    @Override
    public MyModel getEntityInstance(){
        return this;
    }
}

```

APA Repository
```java
public class MyModelRepository extends ApaRepository<MyModel>{

    public MyModelRepository(Class classObject){
        super(classObject);
    }

    @Override
    protected ApaRepository getInstance() {
        return this;
    }
}

```

Usage
```java
MyModel m1 = new MyModel(1, "same text", 1, 23.32);
MyModelRepository repo = new MyModelRepository(MyModel.class);
repo.save(m1);

repo.getAll(); // get all records

```
