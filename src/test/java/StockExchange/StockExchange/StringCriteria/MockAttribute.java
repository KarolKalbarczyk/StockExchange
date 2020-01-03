package StockExchange.StockExchange.StringCriteria;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import java.lang.reflect.Member;

class MockAttribute<T,K> implements SingularAttribute<T,K> {
        String name;

public MockAttribute(String name) {
        this.name = name;
        }

@Override
public boolean isId() {
        return false;
        }

@Override
public boolean isVersion() {
        return false;
        }

@Override
public boolean isOptional() {
        return false;
        }

@Override
public Type<K> getType() {
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
public ManagedType<T> getDeclaringType() {
        return null;
        }

@Override
public Class<K> getJavaType() {
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
public Class<K> getBindableJavaType() {
        return null;
        }
        }