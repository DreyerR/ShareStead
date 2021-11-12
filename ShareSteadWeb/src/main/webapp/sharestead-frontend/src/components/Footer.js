import React from 'react'

const Footer = () => {

    let year = new Date().getFullYear();

    const footerColor = {
        backgroundColor: "rgba(0, 0, 0, 0.2)"
    }

    return (
        <div fixed="bottom" className="text-center text-muted p-2" style={footerColor}>
            {year} - All rights reserved by ShareStead
        </div>
    )
}

export default Footer
