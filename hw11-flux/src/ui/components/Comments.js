import React from 'react';
import Header from "./Header";
import {NavLink, useNavigate, useParams} from "react-router-dom";
import {fetchNdjson} from "../api/ApiFunctions";

function withRouter(Component) {
    function ComponentWithRouter(props) {
        let params = useParams()
        let navigate = useNavigate();
        return <Component {...props} params={params} navigate={navigate}/>
    }

    return ComponentWithRouter
}

class Comments extends React.Component {

    constructor() {
        super();
        this.state = {comments: []};
    }

    componentDidMount() {
        fetchNdjson(this, "/api/book/" + this.props.params.id + "/comment", 'comments')
            .then(r => {
                console.log(r)
            });
    }

    render() {
        return (
            <div>
                <Header title={'Comments'}/>
                <div>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Text</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.comments.map((comment, i) => (
                                <tr key={i}>
                                    <td>{comment.id}</td>
                                    <td>{comment.text}</td>
                                    <td>
                                        <a onClick={()=>this.deleteComment(this.props.params.id, comment.id)}>Delete</a>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
                <NavLink to={"/book"}>Back</NavLink>
            </div>
        )
    }

    deleteComment(bookId, id) {
        fetch('/api/book/' + bookId + '/comment/' + id, {
            method: 'DELETE',
        }).then(_ => this.componentDidMount());
    }
}

const HOCComments = withRouter(Comments);

export default HOCComments;