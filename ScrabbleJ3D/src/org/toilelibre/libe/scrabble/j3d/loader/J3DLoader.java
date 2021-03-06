package org.toilelibre.libe.scrabble.j3d.loader;

import com.sun.j3d.loaders.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.media.j3d.BranchGroup;

import org.toilelibre.libe.scrabble.s3d.loader.IS3DLoader;
import org.toilelibre.libe.scrabble.s3d.loader.S3DLoaderException;
import org.web3d.j3d.loaders.BaseLoader;

public final class J3DLoader implements IS3DLoader {

    public J3DLoader () {

    }

    public Object init (final String clazzName, final String fichier3D)
            throws S3DLoaderException {
        try {
            Scene scene = null;
            BranchGroup forme3D = null;
            BaseLoader loader = null;
            final ClassLoader cld = Thread.currentThread ()
                    .getContextClassLoader ();
            final URL url = cld.getResource (fichier3D);
            final String absoluteFile = new File (url.toURI ())
                    .getAbsolutePath ();
            final Class<?> clazz = Class.forName (clazzName);

            loader = (BaseLoader) clazz.newInstance ();

            scene = loader.load (absoluteFile);

            if (scene != null) {
                forme3D = scene.getSceneGroup ();
            }
            return forme3D;
        } catch (final FileNotFoundException e) {
            throw new J3DLoaderException (e);
        } catch (final ClassNotFoundException e) {
            throw new J3DLoaderException (e);
        } catch (final URISyntaxException e) {
            throw new J3DLoaderException (e);
        } catch (final InstantiationException e) {
            throw new J3DLoaderException (e);
        } catch (final IllegalAccessException e) {
            throw new J3DLoaderException (e);
        }
    }

}
