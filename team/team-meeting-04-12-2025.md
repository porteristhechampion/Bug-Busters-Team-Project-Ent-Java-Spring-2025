# Meeting Notes 04-12-2025

### Attendance:

- Alison
- Jared
- Justin
- Porter

### Summary:

- Based on Alison research we decided to have the user upload an image and text and then return to them a url with the completed meme
- We set up an Elastic Bean Stalk on AWS with MySQL 8 Database with a lot of debugging and issues
- Created an S3 Bucket for the project
- We created a IAM Policy to allow our local and the EBS to have access to the S3 Bucket
- Created a schema and SQL script. Then ran the script in the database on AWS
- Added our IPs to the security for the database and attached the database to our Intellij
- Practiced using Git Flow for development
- Identified the work needed to complete the project and broke it up to individual tasks:
  1. Alison agreed to learn https://swagger.io and how we can use it to document/present our API
  2. Jared agreed to figure out how to modify Alison's S3 demo to work with as an API
  3. Justin agreed to update the documentation/meeting notes ect...
  4. Porter agreed to figure out how to use the image overlay that Alison found in her research


### Links:
- [Example meme generator](https://imgflip.com/memegenerator)
- [Example AWS Code for image overlay](https://github.com/aws-samples/java-meme-generator-sample/blob/master/projects/MemeWorker/src/com/amazonaws/memes/ImageOverlay.java)
- [Alison's S3 Demo](https://github.com/ajfait/aws-s3-file-upload)

### TODO:
- [ ] Learn Swagger.io
- [ ] Modify S3 Demo to work with an API
- [ ] Update Documentation
- [ ] Figure out Image Overlay