import React, {useEffect, useState} from 'react';
import Header from "./Header";
import withRouter from "../function/ComponentWithRouter";
import {NavLink} from "react-router-dom";

const Book = (props) => {
    const [book, setBook] = useState({id: '', title: '', author: '', genres: []});
    const [authors, setAuthors] = useState([]);
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        async function fetchData() {
            (await fetch('/api/book/' + props.params.id))
                .json()
                .then(res => setBook(res));
            (await fetch('/api/author'))
                .json()
                .then(res => setAuthors(res));
            (await fetch('/api/genre'))
                .json()
                .then(res => setGenres(res));
        }
        fetchData().then();
    }, []);

    const saveBook = async (id) =>{
        let titleValue = document.getElementById("title-input").value
        let authorValue = document.getElementById("author-input").value
        let genresSelected = document.getElementById("genres-input").selectedOptions
        let genreValues = Array.from(genresSelected).map(({value}) => value);
        console.log("editing book");
        let response = await fetch('/api/book/' + id, {
            method: 'PUT',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({title: titleValue, authorId: authorValue, genresIds: genreValues})
        }).then(response => response.json());
        console.log("book edited" + response);
        props.navigate('/book');
        console.log("navigating to /book");
    }

    return (
        <div>
            <Header title={'Book'}/>
            {
                <div>
                    <div className="row">
                        <label htmlFor="id-input">ID:</label>
                        <input id="id-input" type="text" readOnly="readonly" value={`${book.id}`}/>
                    </div>

                    <div className="row">
                        <label htmlFor="title-input">Title:</label>
                        <input id="title-input" name="title" type="text" value={`${book.title}`}
                               onChange= {e => setBook({
                                   id: book.id,
                                   title: e.target.value,
                                   author: book.author,
                                   genres: book.genres
                               })}
                        />
                    </div>

                    <div className="row">
                        <label htmlFor="author-input">Author:</label>
                        <select id="author-input" name="authorId">
                            <option value="0">select author</option>
                            {
                                authors.map((author) => (
                                    <option value={`${author.id}`} selected={book.author === author.fullName}>
                                        {author.fullName}
                                    </option>))
                            }
                        </select>
                    </div>

                    <div className="row">
                        <label htmlFor="genres-input">Genres:</label>
                        <select id="genres-input" multiple="multiple" name="genresIds">
                            {
                                genres.map((genre) => (
                                    <option value={`${genre.id}`} selected={book.genres.indexOf(genre.name) >= 0}>
                                        {genre.name}
                                    </option>))
                            }
                        </select>
                    </div>
                    <a onClick={() => saveBook(book.id)}>Save</a>
                </div>
            }
            <NavLink to={"/book"}>Back</NavLink>
        </div>
    )
}

const HOCBook = withRouter(Book);

export default HOCBook;