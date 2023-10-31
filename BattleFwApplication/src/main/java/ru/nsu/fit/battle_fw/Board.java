package ru.nsu.fit.battle_fw;

public class Board {
    private int rows; // Количество строк на игровом поле
    private int columns; // Количество столбцов на игровом поле
    private Card[][] cards; // Массив карт, представляющих поле

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cards = new Card[rows][columns]; // Инициализация массива карт
    }

    // Метод для размещения карты на игровом поле
    public void placeCard(int row, int column, Card card) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            cards[row][column] = card;
        }
    }

    // Метод для получения карты с игрового поля
    public Card getCard(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            return cards[row][column];
        } else {
            return null;
        }
    }
}
