package com.dgtz.api.live.switcher;

import com.dgtz.api.live.switcher.beans.BasicResponse;
import com.dgtz.api.live.switcher.beans.Publish;
import com.dgtz.api.live.switcher.constants.Errors;
import org.freedesktop.gstreamer.*;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI;
import org.freedesktop.gstreamer.lowlevel.GstAPI;
import org.freedesktop.gstreamer.lowlevel.GstElementAPI;
import org.freedesktop.gstreamer.lowlevel.GstGhostPadAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * BroCast.
 * Copyright: Sardor Navruzov
 * 2013-2016.
 */
@RestController
public class CoreController {
    private static final Logger log = LoggerFactory.getLogger(CoreController.class);
    private static Pipeline pipeline;
    private static Element sel, src,videotestsrc, videotestsrc2;

    @RequestMapping(value = "/live/switch", method = RequestMethod.GET)
    public BasicResponse playBroadcasterURL(@RequestParam("sink") String sinkName,
                                            @RequestParam("link") int link) {
        log.info("Player variable: {} {}", sinkName, link);
        BasicResponse basicResponse = new BasicResponse();
        try {

            String pipeStr = "videotestsrc pattern=0 ! queue ! s.sink0"+
            " videotestsrc pattern=1 ! queue ! s.sink1"+
            " input-selector name=s ! autovideosink";


                /*pipeline = new Pipeline("switcher");
                selector = ElementFactory.make("input-selector", "sel");

                videotestsrc = ElementFactory.make("videotestsrc", null);
                videotestsrc.set("pattern", "0");

                videotestsrc2 = ElementFactory.make("videotestsrc", null);
                videotestsrc2.set("pattern", "snow");*/


                /*
                pipeline =
                        Pipeline.launch(
                                "videotestsrc pattern=0 " +
                                        "! video/x-raw,framerate=25/1 " +
                                        "! x264enc " +
                                        "! queue " +
                                        "! sel. videotestsrc pattern=\"snow\" " +
                                        "! video/x-raw,framerate=25/1 " +
                                        "! x264enc " +
                                        "! queue " +
                                        "! sel. input-selector name=sel " +
                                        "! flvmux streamable=true name=mux " +
                                        "! rtmpsink location=\"rtmp://localhost/live/retro\" async=false");

*/

                /*pipeline =
                        Pipeline.launch(
                                "rtmpsrc location=\"rtmp://localhost:1940/vod0/file1.mp4\" " +
                                        "! decodebin " +
                                        "! video/x-h264,profile=baseline,framerate=25/1,interlace-mode=progressive " +
                                        "! queue " +
                                        "! sel. rtmpsrc location=\"rtmp://localhost/live/retro0\" " +
                                        "! flvdemux " +
                                        "! audio/mpeg " +
                                        "! queue " +
                                        "! sel. rtmpsrc location=\"rtmp://localhost/live/retro1\" " +
                                        "! flvdemux " +
                                        "! video/x-h264,profile=baseline,framerate=25/1,interlace-mode=progressive " +
                                        "! queue " +
                                        "! sel. input-selector name=sel cache-buffers=true sync-streams=true sync-mode=clock " +
                                        "! flvmux streamable=true name=mux " +
                                        "! rtmpsink location=\"rtmp://localhost/live/retro\" sync=false");*/

            String test1 = "rtmp://localhost:1940/vod1/file1.mp4";
            String test2 = "rtmp://localhost:1940/vod0/file1.mp4";
            if(pipeline==null || pipeline.getState()==State.NULL) {
                pipeline =
                        Pipeline.launch(
                                "rtmpsrc location=\"rtmp://localhost:1940/vod1/file1.mp4\" " +
                                        "! decodebin ! avenc_aac ! audio/mpeg ! queue ! mux. " +
                                        "rtmpsrc location=\"rtmp://localhost:1940/vod1/file1.mp4\" " +
                                        "! decodebin ! x264enc bitrate=300 bframes=0 byte-stream=false aud=true tune=zerolatency ! video/x-h264,profile=baseline ! queue ! sel. " +
                                        "rtmpsrc location=\"rtmp://localhost:1940/speed/sp\" " +
                                        "! decodebin ! x264enc bitrate=300 bframes=0 byte-stream=false aud=true tune=zerolatency ! video/x-h264,profile=baseline ! queue ! sel. " +
                                        "input-selector name=sel cache-buffers=true sync-streams=true sync-mode=clock " +
                                        "! flvmux streamable=true name=mux " +
                                        "! rtmpsink location=\"rtmp://localhost/live/retro\" sync=false");


                sel = pipeline.getElementByName("sel");
                sel.set("active-pad", sel.getStaticPad(sinkName));

                pipeline.getBus()
                        .connect((Bus.INFO) (source1, code, message) -> System.out.println(message));

                pipeline.getBus()
                        .connect((Bus.WARNING) (source1, code, message) -> System.out.println(message));

                pipeline.getBus()
                        .connect((Bus.ERROR) (source1, code, message) -> System.out.println(message));

            } else if(pipeline.getState() == State.PLAYING){

                pipeline.pause();

                //Pad pad = sel.getStaticPad(sinkName);
                //pad.emit();
                sel = pipeline.getElementByName("sel");
                sel.set("active-pad", sel.getStaticPad(sinkName));
                //pad.("block");
                //pad.emit("switch", pad, -1, -1);
                //sel.set("active-pad", sel.getStaticPad(sinkName));
                //pipeline.pause();
                System.out.println(pipeline.getState());
            }



                /*pipeline =
                        Pipeline.launch(
                                "rtmpsrc location=\"rtmp://localhost:1940/vod0/file1.mp4\" " +
                                        "! decodebin " +
                                        "! x264enc " +
                                        "! video/x-h264 " +
                                        "! queue " +
                                        "! mux. flvmux name=mux " +
                                        "! rtmpsink location=\"rtmp://localhost/live/retro\" sync=false");


                System.out.println(pipeline.getState());
                System.out.println(Arrays.toString(pipeline.getElements().toArray()));*/

            System.out.println(Arrays.toString(pipeline.getSources().toArray()));
            System.out.println(Arrays.toString(pipeline.getSinkPads().toArray()));
            System.out.println(Arrays.toString(pipeline.getSrcPads().toArray()));
            System.out.println(Arrays.toString(pipeline.getSinks().toArray()));
            System.out.println(Arrays.toString(pipeline.getPads().toArray()));
            System.out.println(Arrays.toString(pipeline.getElements().toArray()));








            //Pad pad = sel.getStaticPad(sinkName);
            //pad.setActive(true);

            //Pad pad1 = sel.getStaticPad("sink_1");
            //pad.setActive(true);

            //System.out.println(Collections.singletonList(pad.listPropertyNames()));
            //System.out.println(pad.get());

            //sel.set("active-pad", pad);


            pipeline.play();
            System.out.println(pipeline.getState());

        } catch (Exception e) {
            e.printStackTrace();
            basicResponse = new BasicResponse(Errors.SYSTEM_FAIL);
        }

        return basicResponse;
    }

    /*private static void dynamic_bin_replacement(Pipeline pipe, Element dest_bin, Element src_bin_new, Element src_bin_old) {
        pipe.pause();
        //src_bin.unlink(dst_bin_old);
        pipe.remove(src_bin_old);
        pipe.add(dst_bin_new);
        dst_bin_new.syncStateWithParent();
        src_bin.link(dst_bin_new);
        pipe.ready();
        pipe.play();
    }*/

    @RequestMapping(value = "/live/cam/switch", method = RequestMethod.GET)
    public BasicResponse playBroadcasterURL(@RequestParam("mcam") long sinkName,
                                            @RequestParam("cam1id") long id1,
                                            @RequestParam("cam2id") long id2,
                                            @RequestParam("cam3id") long if3,
                                            @RequestParam("cam4id") long id4)
    {
        BasicResponse basicResponse = new BasicResponse();
    try {

        String test1 = "rtmp://localhost:1940/vod1/file1.mp4";
        String test2 = "rtmp://localhost:1940/vod0/file1.mp4";

        if(pipeline==null || pipeline.getState()==State.NULL){
            pipeline =
                    Pipeline.launch(
                            "rtmpsrc location=\"rtmp://localhost:1940/vod1/file1.mp4\" " +
                                    "! decodebin ! avenc_aac ! audio/mpeg ! queue ! mux. " +
                                    "rtmpsrc name=rtmpsrc " +
                                    "! decodebin ! x264enc bitrate=300 bframes=0 byte-stream=false aud=true tune=zerolatency " +
                                    "! video/x-h264,profile=baseline ! queue ! mux. " +
                                    "flvmux streamable=true name=mux " +
                                    "! rtmpsink location=\"rtmp://localhost/live/retro\" sync=false");

            src = pipeline.getElementByName("rtmpsrc");

            src.set("location", test2);

            pipeline.play();
            /*sel.set("active-pad", sel.getStaticPad(sinkName));*/
        } else if(pipeline.getState() == State.PLAYING){

            pipeline.pause();

            pipeline.unlink(src);
            Element source = ElementFactory.make("rtmpsrc", "rtmpsrc");
            source.set("location", test2);
            source.syncStateWithParent();
            pipeline.link(source);


            pipeline.play();
            //Pad pad = sel.getStaticPad(sinkName);
            //pad.emit();
            //sel.set("active-pad", sel.getStaticPad(sinkName));
            //pad.("block");
            //pad.emit("switch", pad, -1, -1);
            //sel.set("active-pad", sel.getStaticPad(sinkName));
            //pipeline.pause();
            System.out.println(pipeline.getState());
        }


        System.out.println(Arrays.toString(src.getSrcPads().toArray()));

                /*pipeline =
                        Pipeline.launch(
                                "rtmpsrc location=\"rtmp://localhost:1940/vod0/file1.mp4\" " +
                                        "! decodebin " +
                                        "! x264enc " +
                                        "! video/x-h264 " +
                                        "! queue " +
                                        "! mux. flvmux name=mux " +
                                        "! rtmpsink location=\"rtmp://localhost/live/retro\" sync=false");


                System.out.println(pipeline.getState());
                System.out.println(Arrays.toString(pipeline.getElements().toArray()));*/

        System.out.println(Arrays.toString(pipeline.getSources().toArray()));
        System.out.println(Arrays.toString(pipeline.getSinkPads().toArray()));
        System.out.println(Arrays.toString(pipeline.getSrcPads().toArray()));
        System.out.println(Arrays.toString(pipeline.getSinks().toArray()));
        System.out.println(Arrays.toString(pipeline.getPads().toArray()));
        System.out.println(Arrays.toString(pipeline.getElements().toArray()));



        //Pad pad = sel.getStaticPad(sinkName);
        //pad.setActive(true);

        //Pad pad1 = sel.getStaticPad("sink_1");
        //pad.setActive(true);

        //System.out.println(Collections.singletonList(pad.listPropertyNames()));
        //System.out.println(pad.get());

        //sel.set("active-pad", pad);



        System.out.println(pipeline.getState());

    } catch (Exception e) {
        e.printStackTrace();
        basicResponse = new BasicResponse(Errors.SYSTEM_FAIL);
    }

    return basicResponse;
    }

    @RequestMapping(value = "/live/thumb", method = RequestMethod.GET)
    public Integer playHookNginx(@RequestParam String path,
                                 @RequestParam String name,
                                 @RequestParam String app) {
        log.info("Player variable: {}, {}, {}", new Object[]{path,name,app});
        Integer error = 0;
        try {

            System.out.println("---=---");

        } catch (Exception e) {
            e.printStackTrace();
            error = 1;
        }

        return error;
    }

}
