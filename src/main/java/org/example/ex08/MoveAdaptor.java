package org.example.ex08;

// 어댑터 -> 걸러내는 역할 (ex 220V를 5V로)
public abstract class MoveAdaptor implements Movable {
    @Override
    public void down() { }
}
