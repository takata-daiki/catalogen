/*
    Copyright 1996-2009 Ariba, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    $Id: //ariba/platform/ui/opensourceui/examples/Demo/app/Toc.java#1 $
*/

package app;

import ariba.ui.aribaweb.core.AWComponent;
import ariba.ui.wizard.core.Wizard;
import ariba.ui.wizard.component.WizardUtil;
import ariba.util.core.MapUtil;

import java.util.Map;

public class Toc extends AWComponent
{
    
    public AWComponent startWizard ()
    {
        Map wizardContext = MapUtil.map();
        Wizard wizard = new Wizard
            ("gallery/wizard/WizardExample", wizardContext, resourceManager());
        return WizardUtil.startWizard(wizard, requestContext());        
    }

}
