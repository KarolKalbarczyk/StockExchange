package StockExchange.StockExchange.StringCriteria;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;
import java.lang.reflect.Member;

public class MockCollectionAttribute implements CollectionAttribute {

    String name;

    public MockCollectionAttribute(String name) {
        this.name = name;
    }

    @Override
    public CollectionType getCollectionType() {
        return null;
    }

    @Override
    public Type getElementType() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersistentAttributeType getPersistentAttributeType() {
        return null;
    }

    @Override
    public ManagedType getDeclaringType() {
        return null;
    }

    @Override
    public Class getJavaType() {
        return null;
    }

    @Override
    public Member getJavaMember() {
        return null;
    }

    @Override
    public boolean isAssociation() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public BindableType getBindableType() {
        return null;
    }

    @Override
    public Class getBindableJavaType() {
        return null;
    }
}
