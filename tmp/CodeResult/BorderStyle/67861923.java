/*
 * Copyright (c) 2012-2013, bad robot (london) ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.excel.style;

import static org.apache.poi.ss.usermodel.CellStyle.*;

public enum BorderStyle {

    None(BORDER_NONE),
    ThinSolid(BORDER_THIN),
    MediumSolid(BORDER_MEDIUM),
    ThickSolid(BORDER_THICK);

    private short poiStyle;

    BorderStyle(short poiStyle) {
        this.poiStyle = poiStyle;
    }

    public Short getPoiStyle() {
        return poiStyle;
    }
}
