package io.ap4k.component;

import io.ap4k.Generator;
import io.ap4k.Resources;
import io.ap4k.component.config.CompositeConfig;
import io.ap4k.component.config.EditableCompositeConfig;
import io.ap4k.component.visitor.AddRuntimeToComponent;
import io.ap4k.config.ConfigKey;
import io.ap4k.config.Configuration;
import io.ap4k.config.KubernetesConfig;

public class ComponentGenerator implements Generator<CompositeConfig> {

  private static final String COMPONENT = "component";
  public static final ConfigKey<String> RUNTIME_TYPE = new ConfigKey<>("RUNTIME_TYPE", String.class);

  private final Resources resources;

  public ComponentGenerator () {
    this(new Resources());
  }
  public ComponentGenerator(Resources resources) {
    this.resources = resources;
  }


  @Override
  public void generate(CompositeConfig config) {
     addVisitors(config);
  }

  @Override
  public boolean accepts(Class<? extends Configuration> type) {
   return type.equals(CompositeConfig.class) ||
   type.equals(EditableCompositeConfig.class);
  }

  private void addVisitors(KubernetesConfig config) {
      String type = config.getAttribute(RUNTIME_TYPE);
      if (type != null) {
        resources.accept(COMPONENT,new AddRuntimeToComponent(type));
      }
  }

}