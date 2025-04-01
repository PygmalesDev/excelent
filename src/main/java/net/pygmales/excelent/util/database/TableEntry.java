package net.pygmales.excelent.util.database;

public record TableEntry<T>(TableField field, T value) {}
