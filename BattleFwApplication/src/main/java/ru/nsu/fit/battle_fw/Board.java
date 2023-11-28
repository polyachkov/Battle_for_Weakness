package ru.nsu.fit.battle_fw;

import ru.nsu.fit.battle_fw.elders.Card;

import java.io.Serializable;

public class Board implements Serializable {
    private int rows; // Количество строк на игровом поле
    private int columns; // Количество столбцов на игровом поле
    private Card[][] cards; // Массив карт, представляющих поле

    public Board() {}

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Card[][] getCards() {
        return cards;
    }

    public void setCards(Card[][] cards) {
        this.cards = cards;
    }

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
