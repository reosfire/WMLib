package ru.reosfire.wmlib.listening;

public interface MessageReceiver
{
    void OnMessage(String message, MessageListener listener);
}