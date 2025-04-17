import {useNavigate, useParams} from "react-router-dom";
import React from "react";

function withRouter(Component) {
    function ComponentWithRouter(props) {
        let params = useParams()
        let navigate = useNavigate();
        return <Component {...props} params={params} navigate={navigate}/>
    }

    return ComponentWithRouter
}
export default withRouter