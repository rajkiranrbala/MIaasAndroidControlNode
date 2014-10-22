package com.sjsu.taas.rest;

import com.sjsu.taas.controller.AndroidEmulatorManager;
import com.sjsu.taas.controller.Status;
import com.sjsu.taas.result.CreateAvdActionResult;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("androidcontrol")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("status")
    @Produces(MediaType.APPLICATION_JSON)
    public Status getStatus() throws Exception {
        return AndroidEmulatorManager.getStatus();
    }
    
    @GET
    @Path("terminate")
    public void terminate()  {
        AndroidEmulatorManager.terminate();
        Main.stopServer();
    }

    @GET
    @Path("startemulator/{version}/{memory}")
    @Produces(MediaType.APPLICATION_JSON)
    public CreateAvdActionResult startEmulator(@PathParam("version") int version, @PathParam("memory") int memory) {

        try {
            return new CreateAvdActionResult(AndroidEmulatorManager.createAvd(version, memory));
        } catch (Exception e) {
            return new CreateAvdActionResult(e);
        }
    }

    @GET
    @Path("stopemulator/{id}")
    public Response stopEmulator(@PathParam("id") String id) {

        try {
            AndroidEmulatorManager.closeEmulator(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    @POST
    @Path("launchurl/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response startUrl(@PathParam("id") String id, @FormParam("url") String url) {
        try {
            AndroidEmulatorManager.getEmulator(id).launchUrl(url);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("install/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @PathParam("id") String id,
            @FormDataParam("apkfile") InputStream uploadedInputStream,
            @FormDataParam("apkfile") FormDataContentDisposition fileDetail) {
        try {
            AndroidEmulatorManager.getEmulator(id).installApplication(uploadedInputStream);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    @POST
    @Path("launch/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response launchApplication(@PathParam("id") String id, @FormParam("appname") String appname) {
        try {
            AndroidEmulatorManager.getEmulator(id).launchApplication(appname);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("getscreenshot/{id}")
    @Produces("image/png")
    public Response getScreenShot(@PathParam("id") String id) throws Exception {
        return Response.ok(AndroidEmulatorManager.getEmulator(id).getScreenShot()).build();
    }


}
