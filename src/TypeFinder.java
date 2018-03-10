import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class TypeFinder {
	  public static void main(String[] args) throws IOException {

	    parse(readFileToString("/Users/zachalbers/eclipse-workspace/SENG300-Iteration1/TestFiles/test.java"));
	  }

	  public static void parse(String str) {
		  
		  Map options = JavaCore.getOptions();
		  options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);


		  
		  
		  
		  
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setCompilerOptions(options);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			

			parser.setEnvironment(null, null, null, true);
			parser.setUnitName("test.java");
			

			
			
			 
			 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
			cu.accept(new ASTVisitor() {
	 

				public boolean visit(TypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Declaration: " +name);
					
					System.out.println("This class extends " + node.getSuperclassType());
					
					ITypeBinding e = node.resolveBinding();
					
					if (e.getInterfaces() != null) {
						ITypeBinding[] interfaces = e.getInterfaces();
						for (ITypeBinding i : interfaces) System.out.println("implements Reference: " + i.getName());
					}
					

					return super.visit(node); 
				}
				

				
				
				public boolean visit(VariableDeclarationFragment node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Reference: " + name);
					

					return super.visit(node);
				}
				
		
				public boolean visit(ClassInstanceCreation node) {
					Type name = node.getType();
					System.out.println("Reference: " + name);
					


					return false; // do not continue 
			}
				
				
				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				


			});
	 
		}
	  
	  
	  public static String readFileToString(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	 
			char[] buf = new char[10];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				//System.out.println(numRead);
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
	 
			reader.close();
	 
			return  fileData.toString(); 
	  
	  
  
	}
	  
	  
}
