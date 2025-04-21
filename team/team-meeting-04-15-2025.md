# Meeting Notes 04-15-2025

### Attendance:

- Alison
- Jared
- Justin
- Porter

### Summary:
This was mostly a work session and we completed the following tasks.
- Reviewed Porters ImageOverlay Class
- Created AWSAuthUtil to handle local AWS Authorization for development
- Created a S3ImageService to handle connecting and uploading to AWS S3 bucket 
- Created an UploadImageServlet that solidified out the logic that will be needed for the API
- Tested and Created Memes Using the UploadImageServlet
- Updated the GenericDAO and the DAO Tests to work with the new Schema

### TODO:

- [ ] Convert ImageOverlay to use properties and a PropertiesLoader
- [ ] Convert the ImageOverlayServlet to a RESTFUL API
- [ ] Add Swagger to the project and configure it to display our API