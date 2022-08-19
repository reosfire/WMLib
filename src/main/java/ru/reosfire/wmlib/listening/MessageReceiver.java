package ru.reosfire.wmlib.listening;

public interface MessageReceiver
{
    void onMessage(String message, MessageListener listener);
}