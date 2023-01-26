package business.book;

import business.BookstoreDbException;
import business.JdbcUtils;
import business.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.BookstoreDbException.BookstoreQueryDbException;

public class BookDaoJdbc implements BookDao {

    private static final String FIND_BY_BOOK_ID_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
                    "FROM book " +
                    "WHERE book_id = ?";

    private static final String FIND_BY_CATEGORY_ID_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
            "FROM book " +
            "WHERE category_id = ?";


    private static final String FIND_RANDOM_BY_CATEGORY_ID_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
                    "FROM book " +
                    "WHERE category_id = ? " +
                    "ORDER BY RAND() " +
                    "LIMIT ?";

    private  static  final String FIND_BOOKS_BY_CATEGORY_NAME =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category.category_id\n" +
                    "  FROM  book " +
                    "INNER JOIN category " +
                    "ON category.category_id = book.category_id " +
                    "WHERE name = ?" ;

    private static  final String FIND_SUGGESTED_BOOKS_BY_CATEGORY_NAME =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category.category_id\n" +
            "  FROM  book " +
            "INNER JOIN category " +
            "ON category.category_id = book.category_id " +
            "WHERE name = ? " + " ORDER BY RAND() " + "  LIMIT ?";

    @Override
    public Book findByBookId(long bookId) {
        Book book = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_BOOK_ID_SQL)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = readBook(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding book " + bookId, e);
        }
        return book;
    }

    @Override
    public List<Book> findByCategoryId(long categoryId) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding books by category ID " + categoryId, e);
        }
        return books;
    }

    @Override
    public List<Book> findRandomByCategoryId(long categoryId, int limit) {
        List<Book> books = new ArrayList<>();

        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RANDOM_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            statement.setLong(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem randomly finding books by categoryId " + categoryId, e);
        }
        return books;
    }


    private Book readBook(ResultSet resultSet) throws SQLException {
        long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String description = resultSet.getString("description");
        int price = resultSet.getInt("price");
        int rating = resultSet.getInt("rating");
        boolean is_public = resultSet.getBoolean("is_public");
        boolean is_featured = resultSet.getBoolean("is_featured");
        long categoryId = resultSet.getLong("category_id");
        return new Book(bookId, title, author, description, price, rating, is_public, is_featured, categoryId);
    }

    /**
     * Get books by category name
     * @param name category name
     * @return Array of books
     */
    public List<Book> findBooksByCategoryName(String name) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_CATEGORY_NAME)) {
            statement.setString(1,name);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
                return books;
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding books by categoryName " + name, e);
        }
    }

    public List<Book> findSuggestedBooksByCategoryName(String name,int limit) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SUGGESTED_BOOKS_BY_CATEGORY_NAME)) {
            statement.setString(1,name);
            statement.setLong(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
                return books;
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding suggested books by categoryName " + name, e);
        }
    }


}
