import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";

const Books = () => {

    const [books, setBooks] = useState([]);

    useEffect(() => {
        async function fetchData() {
            (await fetch('/api/book')).json()
                .then(res => setBooks(res));
        }
        fetchData().then();
    }, []);

    const deleteBook = async (id) => {
        await fetch('/api/book/' + id, {
            method: 'DELETE',
        }).then(async () =>
            (await fetch('/api/book'))
                .json()
                .then(res => setBooks(res))
        );
    }

    return (
        <div>
            <Header title={'Books'}/>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Genres</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        books.map((book, i) => (
                            <tr key={i}>
                                <td>{book.id}</td>
                                <td>{book.title}</td>
                                <td>{book.author}</td>
                                <td>{book.genres.join(',')}</td>
                                <td>
                                    <NavLink to={"/book/" + book.id}>Edit</NavLink>
                                    <NavLink to={"/book/" + book.id + "/comment"}>Comments</NavLink>
                                    <a onClick={() => deleteBook(book.id)}>Delete</a>
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
            <NavLink to={"/book/add"}>New Book</NavLink>
            <NavLink to={"/"}>Back</NavLink>
        </div>
    )
}

export default Books;