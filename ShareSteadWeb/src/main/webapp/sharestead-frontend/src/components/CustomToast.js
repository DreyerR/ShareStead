import React from 'react'
import { Toast, ToastContainer } from 'react-bootstrap'

export default function CustomToast(props) {

    const toastCss = {
        zIndex: '1',
        // boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)'
    };

    return (
        <ToastContainer style={toastCss} className="p-3" position={'top-end'}>
            <Toast>
                <Toast.Header className={props.success ? "bg-success text-white" : "bg-danger text-white"} closeButton={false}>
                    <strong className={"mr-auto"}>{props.success ? 'Success' : 'Error'}</strong>
                </Toast.Header>

                <Toast.Body>
                    {props.message}
                </Toast.Body>
            </Toast>
        </ToastContainer>
    )
}
