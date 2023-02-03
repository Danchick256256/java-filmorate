import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/drop-test-data.sql", "/schema.sql", "/data.sql"}),
})
@Slf4j
class FilmoRateApplicationTests {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Test
    public void createUser() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        User user = userStorage.createUser(createdUser);

        Assertions.assertEquals(user.getId(), createdUser.getId());
    }
    @Test
    public void testFindUserById() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        User user = userStorage.getUser(1);

        Assertions.assertEquals(user.getId(), createdUser.getId());
    }

    @Test
    public void updateUser() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        User updatedUser = User.builder()
                .id(1)
                .name("new_name")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.updateUser(updatedUser);

        User user = userStorage.getUser(1);

        Assertions.assertEquals(user.getName(), "new_name");
    }

    @Test
    public void deleteUser() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        userStorage.deleteUser(createdUser.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            userStorage.getUser(1);
        });
    }

    @Test
    public void getAllUsers() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        Assertions.assertEquals(1, (int) userStorage.getAllUsers().count());
    }

    @Test
    public void addFriendGetFriendsDeleteFriends() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        User friend = User.builder()
                .id(2)
                .name("friend")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);
        userStorage.createUser(friend);

        userStorage.addFriend(createdUser.getId(), friend.getId());

        Assertions.assertEquals(List.of(2), userStorage.getFriends(createdUser.getId()).collect(Collectors.toList()));

        userStorage.deleteFriends(createdUser.getId(), friend.getId());

        Assertions.assertEquals(Collections.emptyList(), userStorage.getFriends(createdUser.getId()).collect(Collectors.toList()));
    }


    @Test
    public void createFilm() {
        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        Film film = filmStorage.createFilm(createdFilm);

        Assertions.assertEquals(createdFilm.getId(), film.getId());
    }

    @Test
    public void updateFilm() {
        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        filmStorage.createFilm(createdFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("new-name")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        Film film = filmStorage.updateFilm(updatedFilm);

        Assertions.assertEquals("new-name", film.getName());
    }

    @Test
    public void deleteFilm() {
        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        filmStorage.createFilm(createdFilm);

        filmStorage.deleteFilm(createdFilm.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            filmStorage.getFilm(1);
        });
    }

    @Test
    public void getAllFilms() {
        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        filmStorage.createFilm(createdFilm);

        Assertions.assertEquals(1, (int) filmStorage.getAllFilms().count());
    }

    @Test
    public void addLike() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        filmStorage.createFilm(createdFilm);

        filmStorage.addLike(createdFilm.getId(), createdUser.getId());

        Assertions.assertEquals(1, (int) filmStorage.getLikes(createdFilm.getId()).count());
    }

    @Test
    public void deleteLike() {
        User createdUser = User.builder()
                .id(1)
                .name("user")
                .login("user")
                .email("user@email.com")
                .birthday(LocalDate.parse("1976-08-20"))
                .build();

        userStorage.createUser(createdUser);

        Film createdFilm = Film.builder()
                .id(1)
                .name("nisi eiusmod")
                .description("adipisicing")
                .duration(100)
                .releaseDate(LocalDate.parse("1967-03-25"))
                .mpa(mpaStorage.getMpa(1))
                .genres(List.of(genreStorage.getGenre(1)))
                .build();

        filmStorage.createFilm(createdFilm);

        filmStorage.addLike(createdFilm.getId(), createdUser.getId());

        filmStorage.deleteLike(createdFilm.getId(), createdUser.getId());

        Assertions.assertEquals(0, (int) filmStorage.getLikes(createdFilm.getId()).count());
    }


    @Test
    public void getGenre() {
        Genre genre = genreStorage.getGenre(1);

        Assertions.assertEquals("Комедия", genre.getName());
    }

    @Test
    public void getGenreList() {
        List<Genre> genre = genreStorage.getGenreList().collect(Collectors.toList());

        Assertions.assertEquals(6, genre.size());
    }


    @Test
    public void getMpa() {
        MPA mpa = mpaStorage.getMpa(1);

        Assertions.assertEquals("У фильма нет возрастных ограничений", mpa.getDescription());
    }

    @Test
    public void getMpaList() {
        List<MPA> mpaList = mpaStorage.getMpaList().collect(Collectors.toList());

        Assertions.assertEquals(5, mpaList.size());
    }
}