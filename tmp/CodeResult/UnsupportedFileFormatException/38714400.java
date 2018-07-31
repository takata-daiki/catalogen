/*
 * CADOculus, 3D in the Web
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the MIT Licens as published at
 * http://opensource.org/licenses/mit-license.php
 *
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the MIT Licens
 * along with this program.  If not, see <* http://opensource.org/licenses/mit-license.php>
 *
 */
package de.cadoculus.conversion.impl;

import de.cadoculus.conversion.ConversionContext;
import de.cadoculus.conversion.ConversionException;
import de.cadoculus.conversion.ConversionService;
import de.cadoculus.conversion.FileConverter;
import de.cadoculus.conversion.FileTypeDetector;
import de.cadoculus.conversion.TargetContent;
import de.cadoculus.conversion.UnsupportedFileFormatException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;


/**
 * This is a minimale implementation off the conversion service.
 *
 * @author  cz
 */
public class ConversionServiceImpl implements ConversionService {

    private static Log log = LogFactory.getLog( ConversionServiceImpl.class );
    private final ConversionContextImpl ctx;
    private final Repository repository;
    private final Session session;
    private final Node targetFolder;

    public ConversionServiceImpl( Repository repository, Session session, Node targetFolder ) {
        this.repository = repository;
        this.session = session;
        this.targetFolder = targetFolder;

        this.ctx = new ConversionContextImpl();
        ctx.setTargetFolder( targetFolder );
        ctx.setTargetContent( TargetContent.GEOMETRY_AND_SCENE );

    }

    @Override public void convert( Node file2convert ) throws ConversionException,
        RepositoryException {

        // Check the file type
        FileConverter converter = null;
        final ServiceLoader< FileTypeDetector > sl = ServiceLoader.load( FileTypeDetector.class );

        for ( Iterator< FileTypeDetector > it = sl.iterator(); it.hasNext(); ) {
            FileTypeDetector fileTypeDetector = it.next();

            log.info( "check with " + fileTypeDetector );

            converter = fileTypeDetector.getConverter( file2convert );

            if ( converter != null ) {
                log.info( "type detected for " + file2convert.getName() + " : " +
                    file2convert.getProperty( "jcr:content/jcr:mimeType" ).getString() );

                break;
            }

        }

        if ( converter == null ) {

            throw new UnsupportedFileFormatException( "no FileTypeDetector matches",
                Collections.singleton( file2convert ) );

        }

        log.info( "use converter " + converter );

        converter.setTargetNode( targetFolder );
        converter.setConversionContext( ctx );
        converter.convert( file2convert );

        Set< Node > result = converter.getConvertedGeometries();
        Node ps = converter.getProductStructureNode();

        log.info( "converted " + file2convert + " into " + result );
        log.info( "product strucure " + ps );

        ctx.getConvertedGeometries().addAll( result );
        ctx.getConvertedStructures().add( ps );

        if ( ctx.getConvertedRootStructure() == null ) {
            ctx.setConvertedRootStructure( ps );
        }

    }

    @Override public ConversionContext getConversionContext() {
        return ctx;
    }

}
