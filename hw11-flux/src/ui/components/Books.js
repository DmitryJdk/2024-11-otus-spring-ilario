import React from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import {fetchNdjson} from "../api/ApiFunctions";

class Books extends React.Component {

    constructor() {
        super();
        this.state = {books: []};
    }

    componentDidMount() {
        fetchNdjson(this, "/api/book", 'books').then(r => {
            console.log(r)
        });
    }

    render() {
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
                            this.state.books.map((book, i) => (
                                <tr key={i}>
                                    <td>{book.id}</td>
                                    <td>{book.title}</td>
                                    <td>{book.author}</td>
                                    <td>{book.genres.join(',')}</td>
                                    <td>
                                        <NavLink to={"/book/" + book.id}>Edit</NavLink>
                                        <NavLink to={"/book/" + book.id + "/comment"}>Comments</NavLink>
                                        <a onClick={() => this.deleteBook(book.id)}>Delete</a>
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

    deleteBook(id) {
        fetch('/api/book/' + id, {
            method: 'DELETE',
        }).then(_ => this.componentDidMount());
    }

}

export default Books;