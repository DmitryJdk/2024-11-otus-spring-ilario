import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import withRouter from "../function/ComponentWithRouter";

const Comments = (props) => {

    const [comments, setComments] = useState([{}]);
    const [bookId, setBookId] = useState('');

    useEffect(() => {
        async function fetchData() {
            (await fetch('/api/book/' + props.params.bookId + '/comment')).json()
                .then(res => setComments(res));
            setBookId(props.params.bookId);
        }

        fetchData().then();
    }, []);

    const deleteComment = async (bookId, commentId) => {
        fetch('/api/book/' + bookId + '/comment/' + commentId, {
            method: 'DELETE',
        }).then(async () =>
            (await fetch('/api/book/' + props.params.bookId + '/comment')).json()
                .then(res => setComments(res))
        );
    }


    return (
        <div>
            <Header title={'Comments'}/>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Text</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        comments.map((comment, i) => (
                            <tr key={i}>
                                <td>{comment.id}</td>
                                <td>{comment.text}</td>
                                <td>
                                    <NavLink to={"/book/" + bookId + "/comment/" + comment.id}>Edit</NavLink>
                                    <a onClick={() => deleteComment(bookId, comment.id)}>Delete</a>
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
            <NavLink to={"/book/" + bookId + "/comment/add"}>New Comment</NavLink>
            <NavLink to={"/book"}>Back</NavLink>
        </div>
    )
}

const HOCComments = withRouter(Comments);

export default HOCComments;