(defproject mala "0.4.0"
  :description "A integrated project template for building and designing UIs in Clojurescript"
  :url "https://github.com/priyatam/mala"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/priyatam/mala"}
  :min-lein-version "2.5.0"
  :jvm-opts ["-Xms768m" "-server"]
  :global-vars {*warn-on-reflection* false *assert* false}

  :dependencies [[org.clojure/clojure "1.7.0-beta3"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8"]
                 [sablono "0.3.4"]
                 [secretary "1.2.3"]
                 [cljs-http "0.1.30"]
                 [garden "1.2.5"]
                 [facjure/mesh "0.2.7"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [com.cemerick/clojurescript.test "0.2.3"]
            [lein-figwheel "0.3.3"]
            [lein-garden "0.2.5"]
            [lein-pdo "0.1.1"]]

  :source-paths ["src" "env" "target/classes"]

  :clean-targets ^{:protect false} ["resources/public/js" "target/classes"]

  :cljsbuild {:builds [{:id "app"
                        :source-paths ["src" "env/dev"]
                        :compiler {:output-to "resources/public/js/components.js"
                                   :output-dir "resources/public/js/out"
                                   :main dev.repl
                                   :asset-path "js/out"
                                   :optimizations :none
                                   :cache-analysis true
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "dist/components.min.js"
                                   :main ui.main
                                   :optimizations :advanced
                                   :pretty-print false}}]
              :test-commands {"unit-tests" ["phantomjs" :runner]}}

  :garden {:builds [{:id "design"
                     :source-paths ["src/design"]
                     :stylesheet layout.index/styles
                     :compiler {:output-to "resources/public/css/styles.css"
                                :pretty-print true}}
                    {:id "prod"
                     :source-paths ["src/design"]
                     :stylesheet layout.index/styles
                     :compiler {:output-to "dist/styles.min.css"
                                :pretty-print? false}}]}

  :profiles {:dev {:env {:is-dev true}
                   :dependencies [[com.cemerick/clojurescript.test "0.3.3"]
                                  [figwheel "0.3.3"]
                                  [figwheel-sidecar "0.3.3"]
                                  [ring/ring-json "0.3.1"]
                                  [fogus/ring-edn "0.2.0"]
                                  [compojure "1.3.4"]
                                  [javax.servlet/servlet-api "2.5"]
                                  [precursor/om-i "0.1.6"]]
                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :nrepl-port 7888
                              :css-dirs ["resources/public/css"]
                              :open-file-command "emacsclient"
                              :ring-handler dev.mock/api}}}

  :aliases {"clean-all"  ["pdo" "clean," "garden" "clean"]
            "dev" ["pdo" "garden" "auto" "design," "figwheel"]
            "release" ["pdo" "clean," "garden" "once" "prod," "cljsbuild" "once" "prod"]})
