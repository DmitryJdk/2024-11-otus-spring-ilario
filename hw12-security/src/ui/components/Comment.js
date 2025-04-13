import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import withRouter from "../function/ComponentWithRouter";

const Comment = (props) => {

    const [comment, setComment] = useState({id: '', text: ''});
    const [bookId, setBookId] = useState('');

    useEffect(() => {
        async function fetchData() {
            (await fetch('/api/book/' + props.params.bookId + '/comment/' + props.params.commentId)).json()
                .then(res => setComment(res));
            setBookId(props.params.bookId);
        }
        fetchData().then();
    }, []);

    const saveComment = async (bookId, commentId) => {
        let textValue = document.getElementById("text-input").value
        await fetch('/api/book/' + bookId + '/comment/' + commentId, {
            method: 'PUT',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({text: textValue})
        }).then(response => response.json());
        props.navigate('/book/' + bookId + '/comment');
    }


    return (
        <div>
            <Header title={'Comment'}/>
            {
                <div>
                    <div className="row">
                        <label htmlFor="id-input">ID:</label>
                        <input id="id-input" type="text" readOnly="readonly" value={`${comment.id}`}/>
                    </div>

                    <div className="row">
                        <label htmlFor="text-input">Text:</label>
                        <input id="text-input" name="text" type="text" value={`${comment.text}`}
                               onChange={e => setComment({
                                   id: comment.id,
                                   text: e.target.value
                               })}/>
                    </div>

                    <a onClick={() => saveComment(bookId, comment.id)}>Save</a>
                </div>
            }
            <NavLink to={"/book/" + bookId + "/comment"}>Back</NavLink>
        </div>
    )
}

const HOCComment = withRouter(Comment);

export default HOCComment;