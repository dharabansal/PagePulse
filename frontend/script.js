function auditWebsite(){

    let url = document.getElementById("urlInput").value;

    let result = document.getElementById("result");


    result.innerHTML = `
        <div class="card loading-card">
            <div class="spinner"></div>
            <h3>Analyzing website...</h3>
            <p>Please wait while we generate your report</p>
        </div>
    `;


    fetch("https://pagepulse-production-d2a3.up.railway.app/api/audit",{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify({
            url:url
        })

    })

    .then(response=>response.json())

    .then(data=>{
    if(data.statusCode !== 200){

        result.innerHTML = `

        <div class="card">

        <h2>Error</h2>

        <p>${data.message}</p>

        <p>Status: ${data.statusCode}</p>

        </div>

        `;

        return;

    }


        result.innerHTML = `

        <div class="card">

            <h2>Audit Result</h2>

            <div class="score">
                SEO Score: ${data.seoScore}/100
            </div>

            <div class="score-bar">

                <div class="score-fill"
                style="width:${data.seoScore}%">
                </div>

            </div>

            <p><b>Website:</b> ${data.url}</p>

            <p><b>Title:</b> ${data.title}</p>

            <p><b>Status Code:</b> ${data.statusCode}</p>

            <p><b>Response Time:</b> ${data.responseTime} ms</p>

            <p><b>Word Count:</b> ${data.wordCount}</p>

            <p><b>H1 Count:</b> ${data.h1Count}</p>

            <p><b>Images Missing Alt:</b> ${data.imagesMissingAlt}</p>

            <p><b>Meta Description:</b> ${data.metaDescriptionPresent ? "Available" : "Not Available"}</p>

            <p><b>Status:</b> ${data.message}</p>

        </div>

        `;


    })


    .catch(error=>{

        result.innerHTML = `

        <div class="card">

        <h3>Error</h3>

        <p>Unable to analyze website</p>

        </div>

        `;

    });


}