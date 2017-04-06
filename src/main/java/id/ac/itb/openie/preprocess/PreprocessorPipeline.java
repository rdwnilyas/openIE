package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.plugins.PluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPreprocessorPipelineElement> preprocessorPipelineElements = new ArrayList<IPreprocessorPipelineElement>();

    public PreprocessorPipeline addPipelineElement(IPreprocessorPipelineElement preprocessorPipelineElement) {
        preprocessorPipelineElements.add(preprocessorPipelineElement);
        return this;
    }

    private void addReaderAndWriterIfNotExist() {
        if (preprocessorPipelineElements.size() > 0) {
            PluginLoader pluginLoader = new PluginLoader();
            pluginLoader.registerAvailableExtensions(IPreprocessorHandler.class);

            // Prepend preprocessor file reader if not exist
            if (!((Preprocessor) preprocessorPipelineElements.get(0)).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Reader")) {
                for (Object iPreprocessorHandler: pluginLoader.getExtensions(IPreprocessorHandler.class)) {
                    IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) iPreprocessorHandler;
                    String pluginName = preprocessorHandler.getPluginName();

                    if (pluginName.equalsIgnoreCase("Preprocessor File Reader")) {
                        Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                        preprocessorPipelineElements.add(0, preprocessor);
                    }
                }
            }

            if (!((Preprocessor) preprocessorPipelineElements.get(preprocessorPipelineElements.size() - 1)).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Writer")) {
                for (Object iPreprocessorHandler: pluginLoader.getExtensions(IPreprocessorHandler.class)) {
                    IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) iPreprocessorHandler;
                    String pluginName = preprocessorHandler.getPluginName();

                    if (pluginName.equalsIgnoreCase("Preprocessor File Writer")) {
                        Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                        preprocessorPipelineElements.add(preprocessor);
                    }
                }
            }
        }
    }

    public void execute() throws Exception {
        System.out.println("Running preprocessor pipeline...");

        HashMap<File, String> pipeQueue = null;
        HashMap<File, String> nextPipeQueue = null;

        addReaderAndWriterIfNotExist();

        for (IPreprocessorPipelineElement preprocessorPipelineElement: preprocessorPipelineElements) {
            if (pipeQueue == null) {
                pipeQueue = new HashMap<File, String>();
                nextPipeQueue = new HashMap<File, String>();

                HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(null, null);
                pipeQueue.putAll(preprocessed);
            } else {
                Iterator<Map.Entry<File, String>> it = pipeQueue.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<File, String> pair = it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());

                    HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(pair.getKey(), pair.getValue());

                    nextPipeQueue.putAll(preprocessed);

                    it.remove(); // avoids a ConcurrentModificationException
                }

                pipeQueue = nextPipeQueue;
                nextPipeQueue = new HashMap<File, String>();
            }
        }
    }
}
