import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import withRouter from "../function/ComponentWithRouter";

const NewBook = (props) => {

    const [book, setBook] = useState({title: '', author: '', genres: []});
    const [authors, setAuthors] = useState([]);
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        async function fetchData() {
            (await fetch(`/api/author`)).json()
                .then(res => setAuthors(res));
            (await fetch(`/api/genre`)).json()
                .then(res => setGenres(res));
        }

        fetchData().then();
    }, []);

    const saveBook = async () => {
        let titleValue = document.getElementById("title-input").value
        let authorValue = document.getElementById("author-input").value
        let genresSelected = document.getElementById("genres-input").selectedOptions
        let genreValues = Array.from(genresSelected).map(({value}) => value);
        await fetch('/api/book', {
            method: 'POST',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({title: titleValue, authorId: authorValue, genresIds: genreValues})
        }).then(response => response.json());
        props.navigate('/book');
    }

    return (
        <div>
            <Header title={'Book'}/>
            {
                <div>
                    <div className="row">
                        <label htmlFor="title-input">Title:</label>
                        <input id="title-input" name="title" type="text"
                               onChange={e => setBook({
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
                                    <option value={`${author.id}`}>{author.fullName}</option>))
                            }
                        </select>
                    </div>
                    <div className="row">
                        <label htmlFor="genres-input">Genres:</label>
                        <select id="genres-input" multiple="multiple" name="genresIds">
                            {
                                genres.map((genre) => (
                                    <option value={`${genre.id}`}>{genre.name}</option>))
                            }
                        </select>
                    </div>
                    <a onClick={() => saveBook()}>Save</a>
                </div>
            }
            <NavLink to={"/book"}>Back</NavLink>
        </div>
    )
}

const HOCNewBook = withRouter(NewBook);

export default HOCNewBook;