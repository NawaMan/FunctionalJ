// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.lang.model.element.Element;

import lombok.val;


/** This class is intentionally named in lower case to signal that it should not be used. */
public class common {
    
    public static String extractTargetName(Element element) {
        if (element.getAnnotation(Struct.class) != null) {
            val specTargetName = element.getAnnotation(Struct.class).name();
            val simpleName     = element.getSimpleName().toString();
            return extractTargetName(simpleName, specTargetName);
            
        } else if (element.getAnnotation(Struct.class) != null) {
            val specTargetName = element.getAnnotation(Choice.class).name();
            val simpleName     = element.getSimpleName().toString();
            return extractTargetName(simpleName, specTargetName);
        }
        return null;
    }
    
    public static String extractTargetName(String simpleName, String specTargetName) {
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
    public static List<String> readLocalTypeWithLens(Element element) {
        return element
                .getEnclosingElement()
                .getEnclosedElements().stream()
                .filter (elmt -> isStructOrChoise(elmt))
                .map    (elmt -> extractTargetName(elmt))
                .filter (name -> nonNull(name))
                .collect(toList());
    }
    
    private static boolean isStructOrChoise(Element elmt) {
        return (elmt.getAnnotation(Struct.class) != null)
            || (elmt.getAnnotation(Choice.class) != null);
    }
    
}
