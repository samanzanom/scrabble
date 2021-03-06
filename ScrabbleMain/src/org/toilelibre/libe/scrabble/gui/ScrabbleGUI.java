package org.toilelibre.libe.scrabble.gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.guiengines.IEngine;
import org.toilelibre.libe.scrabble.beans.ScrabbleBeansHelper;
import org.toilelibre.libe.userinteractions.model.UserInteractions;

public final class ScrabbleGUI
{
  private static final String    CREATING_GUI_ERROR = "Erreur lors de la création de l'interface";
  private static final Logger    LOG                = Logger
                                                        .getLogger (ScrabbleGUI.class);

  private static ScrabbleGUI     sgui;

  private final IEngine          engine             = (IEngine) ScrabbleBeansHelper
                                                        .getBean ("guiEngine");

  private final UserInteractions ui                 = (UserInteractions) ScrabbleBeansHelper
                                                        .getBean ("userInteractions");

  public ScrabbleGUI ()
  {
    ScrabbleGUI.sgui = this;
  }

  public static ScrabbleGUI getInstance ()
  {
    return ScrabbleGUI.sgui;
  }

  public static void redirect (final String beanName, final URL url)
  {
    ScrabbleGUI.getInstance ().display (beanName, url);
  }

  public void display (final String beanName, final URL url)
  {
    try
    {
      ScrabbleGUI.LOG.info ("Redirection ("
          + url.toString ().substring (url.toString ().lastIndexOf ('/') + 1)
          + ", " + beanName + ")");
      this.engine.setClient (this.ui.getBean (beanName));
      this.engine.render (url);
      this.ui.updateBindings ();

    } catch (final IllegalAccessException e)
    {
      ScrabbleGUI.LOG.error (ScrabbleGUI.CREATING_GUI_ERROR, e);

    } catch (final InvocationTargetException e)
    {
      ScrabbleGUI.LOG.error (ScrabbleGUI.CREATING_GUI_ERROR, e);
    } catch (final IOException e)
    {
      ScrabbleGUI.LOG.error (ScrabbleGUI.CREATING_GUI_ERROR, e);
    } catch (final InstantiationException e)
    {
      ScrabbleGUI.LOG.error (ScrabbleGUI.CREATING_GUI_ERROR, e);
    } catch (final org.jdom.JDOMException e)
    {
      ScrabbleGUI.LOG.error (ScrabbleGUI.CREATING_GUI_ERROR, e);
    }
  }

  /**
   * @return engine
   */
  public IEngine getEngine ()
  {
    return this.engine;
  }

  public void load ()
  {
    ScrabbleBeansHelper.launchMethod ("fireLoadRunnable");
  }
}
